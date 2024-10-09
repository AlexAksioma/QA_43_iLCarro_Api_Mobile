package helper;

import api_rest.CarController;
import dto.CarsDto;
import io.restassured.response.Response;

public class ReturnCarsDtoApi {

    public static CarsDto returnCarsDto(){
        CarController carController = new CarController();
        carController.login();
        if (carController.tokenDto.getAccessToken() != null) {
            Response response1 = carController.getUserCars();
            return response1.as(CarsDto.class);
        } else
            return null;
    }
}
