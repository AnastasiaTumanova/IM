package org.IM2.magazine.mapper;

import org.IM2.magazine.dto.ProductDTO;
import org.IM2.magazine.models.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);
    List<Product> fromProduct(List<ProductDTO> productDTOlist);
    List<ProductDTO> fromProductList(List<Product> products);

    Product toProduct(ProductDTO dto);
}
