package NayaBazzar.ai.services;

import NayaBazzar.exception.ProductException;
import NayaBazzar.response.ApiResponse;

public interface AiChatBotService {

    ApiResponse aiChatBot(String prompt,Long productId,Long userId) throws ProductException;
}
