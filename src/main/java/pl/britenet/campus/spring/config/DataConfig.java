package pl.britenet.campus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.britenet.campusapiapp.database.DatabaseService;
import pl.britenet.campusapiapp.service.*;

@Configuration
public class DataConfig {

    private final DatabaseService databaseService;

    @Autowired
    public DataConfig(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Bean
    public CartService getCartService() {
        return new CartService(this.databaseService);
    }

    @Bean
    public CartProductService getCartProductServiceService() {
        return new CartProductService(this.databaseService);
    }

    @Bean
    public CategoryService getCategoryService() {
        return new CategoryService(this.databaseService);
    }

    @Bean
    public OrderService getOrderService() {
        return new OrderService(this.databaseService);
    }

    @Bean
    public OrderProductService getOrderProductService() {
        return new OrderProductService(this.databaseService);
    }

    @Bean
    public ProductCategoryService getProductCategoryService() {
        return new ProductCategoryService(this.databaseService);
    }

    @Bean
    public ProductService getProductService() {
        return new ProductService(this.databaseService);
    }

    @Bean
    public UserService getUserService() {
        return new UserService(this.databaseService);
    }

    @Bean
    public ReportService getReportService() {
        return new ReportService(this.databaseService);
    }
}
