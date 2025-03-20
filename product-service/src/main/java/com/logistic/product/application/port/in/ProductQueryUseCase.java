package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.application.port.in.query.ListProductQuery;
import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductQueryUseCase {
  Product findProduct(FindProductQuery query);

  List<Product> findProductList(ListProductQuery query);

  Page<Product> search(SearchProductQuery query);
}
