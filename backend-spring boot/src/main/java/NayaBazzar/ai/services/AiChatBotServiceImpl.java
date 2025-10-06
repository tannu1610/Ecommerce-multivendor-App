package NayaBazzar.ai.services;

import NayaBazzar.exception.ProductException;
import NayaBazzar.mapper.OrderMapper;
import NayaBazzar.mapper.ProductMapper;
import NayaBazzar.model.Cart;
import NayaBazzar.model.Order;
import NayaBazzar.model.Product;
import NayaBazzar.model.User;
import NayaBazzar.repository.CartRepository;
import NayaBazzar.repository.OrderRepository;
import NayaBazzar.repository.ProductRepository;
import NayaBazzar.repository.UserRepository;
import NayaBazzar.response.ApiResponse;
import NayaBazzar.response.FunctionResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiChatBotServiceImpl implements AiChatBotService {

    private final String GEMINI_API_KEY = "AIzaSyA75lT560VYCdVn0KNT1SPN-BRVXvxCGKo";
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-pro:generateContent?key=" + GEMINI_API_KEY;

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // --------------------- FUNCTION DECLARATIONS ---------------------

    private JSONArray createFunctionDeclarations() {
        JSONArray functionDeclarations = new JSONArray();

        functionDeclarations.put(new JSONObject()
                .put("name", "getUserCart")
                .put("description", "Retrieve the user's cart details")
                .put("parameters", new JSONObject()
                        .put("type", "object")
                        .put("properties", new JSONObject()
                                .put("cart", new JSONObject()
                                        .put("type", "string")
                                        .put("description", "Cart Details")
                                )
                        )
                        .put("required", new JSONArray().put("cart"))
                )
        );

        functionDeclarations.put(new JSONObject()
                .put("name", "getUsersOrder")
                .put("description", "Retrieve the user's order details")
                .put("parameters", new JSONObject()
                        .put("type", "object")
                        .put("properties", new JSONObject()
                                .put("order", new JSONObject()
                                        .put("type", "string")
                                        .put("description", "Order Details")
                                )
                        )
                        .put("required", new JSONArray().put("order"))
                )
        );

        functionDeclarations.put(new JSONObject()
                .put("name", "getProductDetails")
                .put("description", "Retrieve product details")
                .put("parameters", new JSONObject()
                        .put("type", "object")
                        .put("properties", new JSONObject()
                                .put("product", new JSONObject()
                                        .put("type", "string")
                                        .put("description", "The Product Details")
                                )
                        )
                        .put("required", new JSONArray().put("product"))
                )
        );

        return functionDeclarations;
    }

    // --------------------- PROCESS FUNCTION CALL ---------------------

    private FunctionResponse processFunctionCall(JSONObject functionCall,
                                                 Long productId,
                                                 Long userId) throws ProductException {
        String functionName = functionCall.getString("name");
        JSONObject args = functionCall.optJSONObject("args");

        FunctionResponse res = new FunctionResponse();
        res.setFunctionName(functionName);

        User user = userRepository.findById(userId).orElse(null);

        switch (functionName) {
            case "getUserCart":
                Cart cart = cartRepository.findByUserId(userId);
                res.setUserCart(cart);
                break;

            case "getUsersOrder":
                List<Order> orders = orderRepository.findByUserId(userId);
                res.setOrderHistory(OrderMapper.toOrderHistory(orders, user));
                break;

            case "getProductDetails":
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductException("product not found"));
                res.setProduct(product);
                break;

            default:
                throw new IllegalArgumentException("Unsupported function: " + functionName);
        }

        return res;
    }

    // --------------------- GEMINI FUNCTION RESPONSE ---------------------

    public FunctionResponse getFunctionResponse(String prompt, Long productId, Long userId) throws ProductException {
        // Prepare tools array
        JSONArray toolsArray = new JSONArray();
        JSONObject toolObject = new JSONObject()
                .put("functionDeclarations", createFunctionDeclarations());
        toolsArray.put(toolObject);

        // Build requestBody
        JSONObject requestBody = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", prompt))
                                )
                        )
                );

        // Wrap tools inside config
        JSONObject configObj = new JSONObject();
        configObj.put("tools", toolsArray);
        requestBody.put("config", configObj);

        // Debug: print request JSON
        System.out.println("Request JSON = " + requestBody.toString(2));

        // HTTP setup
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_URL, entity, String.class);

        // Parse API response
        JSONObject json = new JSONObject(response.getBody());
        System.out.println("\n---- Gemini Raw Response ----\n" + json.toString(2));

        JSONObject candidate = json.getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content");

        JSONArray parts = candidate.getJSONArray("parts");
        JSONObject firstPart = parts.getJSONObject(0);

        FunctionResponse res = new FunctionResponse();

        if (firstPart.has("functionCall")) {
            JSONObject functionCall = firstPart.getJSONObject("functionCall");
            System.out.println("Function call detected: " + functionCall);
            return processFunctionCall(functionCall, productId, userId);
        } else {
            String textResponse = firstPart.optString("text", "No function call detected.");
            System.out.println("Text response: " + textResponse);
            res.setFunctionName("none");
            res.setMessage(textResponse);
            return res;
        }
    }

    // --------------------- MAIN AI CHATBOT FUNCTION ---------------------

    @Override
    public ApiResponse aiChatBot(String prompt, Long productId, Long userId) throws ProductException {
        FunctionResponse functionResponse = getFunctionResponse(prompt, productId, userId);

        JSONObject userMessage = new JSONObject()
                .put("role", "user")
                .put("parts", new JSONArray()
                        .put(new JSONObject().put("text", prompt))
                );

        JSONObject modelMessage = new JSONObject()
                .put("role", "model")
                .put("parts", new JSONArray()
                        .put(new JSONObject()
                                .put("functionCall", new JSONObject()
                                        .put("name", functionResponse.getFunctionName())
                                        .put("args", new JSONObject()
                                                .put("cart", functionResponse.getUserCart() != null ? functionResponse.getUserCart().getId() : JSONObject.NULL)
                                                .put("order", functionResponse.getOrderHistory() != null ? functionResponse.getOrderHistory() : JSONObject.NULL)
                                                .put("product", functionResponse.getProduct() != null ?
                                                        ProductMapper.toProductDto(functionResponse.getProduct()) :
                                                        JSONObject.NULL)
                                        )
                                )
                        )
                );

        JSONObject functionResponseMessage = new JSONObject()
                .put("role", "function")
                .put("parts", new JSONArray()
                        .put(new JSONObject()
                                .put("functionResponse", new JSONObject()
                                        .put("name", functionResponse.getFunctionName())
                                        .put("response", new JSONObject()
                                                .put("name", functionResponse.getFunctionName())
                                                .put("content", functionResponse)
                                        )
                                )
                        )
                );

        JSONArray conversation = new JSONArray()
                .put(userMessage)
                .put(modelMessage)
                .put(functionResponseMessage);

        // Build final request with config.tools
        JSONArray toolsArray = new JSONArray();
        JSONObject toolObject2 = new JSONObject()
                .put("functionDeclarations", createFunctionDeclarations());
        toolsArray.put(toolObject2);

        JSONObject configObj2 = new JSONObject();
        configObj2.put("tools", toolsArray);

        JSONObject finalRequest = new JSONObject()
                .put("contents", conversation)
                .put("config", configObj2);

        System.out.println("Final Request JSON = " + finalRequest.toString(2));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(finalRequest.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> finalResponse = restTemplate.postForEntity(GEMINI_URL, request, String.class);

        JSONObject finalJson = new JSONObject(finalResponse.getBody());
        JSONObject content = finalJson.getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content");

        String text = content.getJSONArray("parts")
                .getJSONObject(0)
                .optString("text", "No text generated.");

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(text);
        return apiResponse;
    }
}
