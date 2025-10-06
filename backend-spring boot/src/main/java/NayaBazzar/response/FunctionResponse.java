//package NayaBazzar.response;
//
//import NayaBazzar.dto.OrderHistory;
//import NayaBazzar.model.Cart;
//import NayaBazzar.model.Product;
//import lombok.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class FunctionResponse {
//    private String functionName;
//    private Cart userCart;
//    private OrderHistory orderHistory;
//    private Product product;
//}




package NayaBazzar.response;

import NayaBazzar.dto.OrderHistory;
import NayaBazzar.model.Cart;
import NayaBazzar.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;

    private String message;   // ✅ added field
    private Object data;      // ✅ added field
}
