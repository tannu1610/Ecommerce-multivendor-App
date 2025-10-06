package NayaBazzar.service;

import NayaBazzar.exception.ProductException;
import NayaBazzar.model.Cart;
import NayaBazzar.model.CartItem;
import NayaBazzar.model.Product;
import NayaBazzar.model.User;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	
	public Cart findUserCart(User user);

}
