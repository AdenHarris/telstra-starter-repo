package au.com.telstra.simcardactivator.SimCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SimCardService {

    private final SimCardRepository simcardRepository;

    @Autowired
    public SimCardService(SimCardRepository simcardRepository)
    {
        this.simcardRepository = simcardRepository;
    }

    public Iterable<SimCard> getSimCards() {

        return simcardRepository.findAll();
    }

    public SimCard getSimCardById(long simCardId) {
        
        return simcardRepository.findById(simCardId); 
    }

    public void saveSimCardToDatabase(SimCard simcard)
    {
        simcardRepository.save(simcard);
    }

    public void sendSimCard(SimCard simCard) throws IOException {

            URL url = new URL("http://localhost:8444/actuate");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            String data = String.format("{\"iccid\": \"%s\"}", simCard.getIccid());
            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            int responseCode = http.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

            if (responseCode == 200) {
                simCard.setActive(true);
            }
            http.disconnect();
        }
    }