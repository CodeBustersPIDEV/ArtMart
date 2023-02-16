package com.artmart.services;

import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.ProductReviewDao;
import com.artmart.interfaces.IProductReviewDao;
import com.artmart.interfaces.IReadyProductDao;
import com.artmart.models.ReadyProduct;
import com.artmart.models.ProductReview;
import java.sql.SQLException;
import java.util.List;

public class ReadyProductService implements IReadyProductDao, IProductReviewDao {

    private final ReadyProductDao readyProductDao = new ReadyProductDao();
    private final ProductReviewDao productReviewDao = new ProductReviewDao();

    @Override
    public List<ReadyProduct> getAllReadyProducts() throws SQLException {
        return readyProductDao.getAllReadyProducts();
    }

    @Override
    public ReadyProduct getReadyProductById(int id) throws SQLException {
        return readyProductDao.getReadyProductById(id);
    }

    @Override
    public int createReadyProduct(ReadyProduct readyProduct) throws SQLException {
        return readyProductDao.createReadyProduct(readyProduct);
    }

    @Override
    public int updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException {
        return readyProductDao.updateReadyProduct(id, readyProduct);
    }

    @Override
    public int deleteReadyProduct(int id) throws SQLException {
        return readyProductDao.deleteReadyProduct(id);
    }

    //ProductReview
    @Override
    public ProductReview getProductReviewById(int id) throws SQLException {
        return productReviewDao.getProductReviewById(id);
    }

    @Override
    public List<ProductReview> getAllProductReviews() throws SQLException {
        return productReviewDao.getAllProductReviews();
    }

    @Override
    public int createProductReview(ProductReview productReview) throws SQLException {
        return productReviewDao.createProductReview(productReview);
    }

    @Override
    public int updateProductReview(int id, ProductReview productReview) throws SQLException {
        return productReviewDao.updateProductReview(id, productReview);
    }

    @Override
    public int deleteProductReview(int id) throws SQLException {
        return productReviewDao.deleteProductReview(id);
    }
}
