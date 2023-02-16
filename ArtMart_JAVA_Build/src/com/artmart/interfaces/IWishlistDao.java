package com.artmart.interfaces;

import com.artmart.models.*;
import java.util.List;

public interface IWishlistDao {

    int createWishlist(Wishlist wishlist);

    Wishlist getWishlist(int id);

    List<Wishlist> getWishlists();

    boolean updateWishlist(Wishlist wishlist);

    boolean deleteWishlist(int id);

}
