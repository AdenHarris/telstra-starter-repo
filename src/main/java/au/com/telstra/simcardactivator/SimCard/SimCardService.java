package au.com.telstra.simcardactivator.SimCard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public List<SimCard> getSimCards() {
        return SimCards;
    }

    public List<SimCard> SimCards = new ArrayList<>(
            Arrays.asList(
                    new SimCard(
                            "1",
                            "SimCard1@gmail.com")));

    public List<SimCard> recieveSimCard(SimCard postRequest) {
        SimCards.add(postRequest);
        return SimCards;
    }

    public void sendSimCard() throws IOException {

        for (int i = 0; i < SimCards.size(); i++) {
            if (SimCards.get(i).isActivated()) {
                continue;
            }
            URL url = new URL("http://localhost:8444/actuate");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            String data = String.format("{\"iccid\": \"%s\"}", SimCards.get(i).getIccid());
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
                SimCards.get(i).setActivated(true);
            }
            http.disconnect();
        }
    }
}