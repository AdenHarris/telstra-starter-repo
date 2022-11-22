package au.com.telstra.simcardactivator.SimCard;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController

public class SimCardController {
    
private final SimCardService simCardService;

@Autowired
public SimCardController(SimCardService simCardService) {
    this.simCardService = simCardService;
}

@RequestMapping(path = "simcard")
public List<SimCard> getSimCards(){
    return simCardService.getSimCards();
}

@RequestMapping(path = "r")
public void recieveSimCards(@RequestBody SimCard simCard) {
    
     simCardService.recieveSimCard(simCard);
    
    try {
        simCardService.sendSimCard();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
    
}

@RequestMapping(path = "send")
public void sendSimCard() throws IOException{
    simCardService.sendSimCard();

}
}
