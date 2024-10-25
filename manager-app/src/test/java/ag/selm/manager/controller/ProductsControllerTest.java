package ag.selm.manager.controller;

import ag.selm.manager.client.BadRequestException;
import ag.selm.manager.client.ProductsRestClient;
import ag.selm.manager.controller.payload.NewProductPayLoad;
import ag.selm.manager.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @InjectMocks
    ProductsController controller;

    @Test
    @DisplayName("Тестирование валидного создание продукта")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {

        //given
        var payload = new NewProductPayLoad("Новый товар", "Описание нового товара");
        var model = new ConcurrentModel();

        // описываем поведение RestClient в случае правильного ответа (тестируем не RestClient, а Controller)
        doReturn(new Product(1, "Новый товар", "Описание нового товара"))
                .when(this.productsRestClient)
                .createProduct("Новый товар", "Описание нового товара");

        //when
        var result = this.controller.createProduct(payload, model);

        //then
        assertEquals("redirect:/catalogue/products/1", result);
        System.out.println("###### result = " + result);

        // проверим, что createProduct действительно был вызван
        verify(this.productsRestClient).createProduct("Новый товар", "Описание нового товара");
        // проверим, что е было больше обращение к createProduct
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("createProduct вернет страницу с ошибками, если запрос невалиден")
    void createProduct_RequestIsInvalid_ReturnsProductFormWithErrors() {

        //given
        var payload = new NewProductPayLoad("   ", null);
        var model = new ConcurrentModel();

        // описываем поведение RestClient в случае ошибки (тестируем не RestClient, а Controller)
        doThrow(new BadRequestException(List.of("Ошибка1", "Ошибка2")))
                .when(this.productsRestClient)
                .createProduct("   ", null);

        //when
        var result = this.controller.createProduct(payload, model);

        //then
        assertEquals("catalogue/products/new_product", result);
        System.out.println("###### result = " + result);
        // проверим, что в model есть payload, который мы задавали (т.е. в методе не должны вноситься изменения в payload)
        assertEquals(payload, model.getAttribute("payload"));
        // проверим, что в model будут ошибки, которые выбросил RestClient
        assertEquals(List.of("Ошибка1", "Ошибка2"), model.getAttribute("errors"));

        // проверим, что createProduct действительно был вызван
        verify(this.productsRestClient).createProduct("   ", null);
        // проверим, что е было больше обращение к createProduct
        verifyNoMoreInteractions(this.productsRestClient);
    }
}