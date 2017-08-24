package com.acme.ws.endpoint;

import com.acme.types.garage.Car;
import com.acme.types.garage.Delivery;
import com.acme.types.garage.ObjectFactory;
import com.acme.types.garage.Result;
import com.acme.types.garage.Truck;
import com.acme.types.garage.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class DeliveryEndpoint { 

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryEndpoint.class);

  private static final String NAMESPACE_URI = "http://acme.com/types/garage";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "delivery")
  @ResponsePayload
  public Result processDelivery(@RequestPayload Delivery delivery) {
    LOGGER.info("Delivery received");

    StringBuilder sb = new StringBuilder();
    String contents = "";
    sb.append("Processing delivery, vehicle contents [");
    Vehicle vehicle = delivery.getVehicle();
    if (vehicle instanceof Car) {
      contents = ((Car) vehicle).getTrunkContents();
    } else if (vehicle instanceof Truck) {
      contents = ((Truck) vehicle).getCabContents();
    }
    sb.append(contents);
    sb.append("]");

    ObjectFactory factory = new ObjectFactory();
    Result result = factory.createResult();
    result.setResult("yes, delivery of [" + contents + "] looks ok");

    LOGGER.info("Sending result='{}'", result.getResult());
    return result;
  }
}

