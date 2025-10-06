package NayaBazzar.service;

import NayaBazzar.exception.ReviewNotFoundException;
import NayaBazzar.model.Product;
import NayaBazzar.model.Review;
import NayaBazzar.model.User;
import NayaBazzar.request.CreateReviewRequest;

import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req,
                        User user,
                        Product product);

    List<Review> getReviewsByProductId(Long productId);

    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws ReviewNotFoundException, AuthenticationException;


    void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException;

}
