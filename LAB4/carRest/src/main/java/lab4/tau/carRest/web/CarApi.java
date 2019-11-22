package lab4.tau.carRest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lab4.tau.carRest.domain.Car;
import lab4.tau.carRest.service.CarManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple web api demo -- try implementning post method
 * 
 * Created by tp on 24.04.17.
 */
@RestController
public class CarApi {

    @Autowired
    CarManager carManager;

    @RequestMapping("/test")
    public String index() {
        return "This run. API work confirmed.";
    }

    @RequestMapping(value = "/car/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Car getCar(@PathVariable("id") Long id) throws SQLException {
        return carManager.getCar(id);
    }

    @RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Car> getCars(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Car> cars = new LinkedList<Car>();
        for (Car p : carManager.getAllCars()) {
            if (f == null) {
                cars.add(p);
            } else if (p.getModel().contains(f)) {
                cars.add(p);
            }
        }
        return cars;
    }

    @RequestMapping(value = "/car",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Car addCar(@RequestBody Car p) {
        if (carManager.addCar(p) < 1) return null;
        return p;
    }

    @RequestMapping(value = "/car/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Long deleteCar(@PathVariable("id") Long id) throws SQLException {
        return new Long(carManager.deleteCar(carManager.getCar(id)));
    }

    // Update car
    @RequestMapping(value = "/car/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Car updateCar(@PathVariable("id") Long id, @RequestBody Car c) throws SQLException {
        c.setId(id);
        if (carManager.updateCar(c) < 1) return null;
        return c;
    }


}
