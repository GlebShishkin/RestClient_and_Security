package ag.selm.manager.controller.payload;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record NewProductPayLoad(
        String title
        , String details) {
}
