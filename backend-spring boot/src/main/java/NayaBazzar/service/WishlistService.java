package NayaBazzar.service;


import NayaBazzar.exception.WishlistNotFoundException;
import NayaBazzar.model.Product;
import NayaBazzar.model.User;
import NayaBazzar.model.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

