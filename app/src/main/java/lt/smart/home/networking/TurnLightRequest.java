package lt.smart.home.networking;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TurnLightRequest {
    String method = "passthrough";
    ParamsEntity params;

    public TurnLightRequest(boolean turnOn) {
        this.params = new ParamsEntity(turnOn ? 1 : 0);
    }

    class ParamsEntity {
        String deviceId = "8006F71CDB62C341277BF463307056231991429D";
        String requestData;

        public ParamsEntity(int state) {
            this.requestData = new Gson().toJson(new RequestDataEntity(state));
        }
    }

    class RequestDataEntity {
        SystemEntity system;

        public RequestDataEntity(int state) {
            this.system = new SystemEntity(state);
        }
    }

    class SystemEntity {
        @SerializedName("set_relay_state")
        RelayStateEntity relayState;

        public SystemEntity(int state) {
            this.relayState = new RelayStateEntity(state);
        }
    }

    class RelayStateEntity {
        int state;

        public RelayStateEntity(int state) {
            this.state = state;
        }
    }
}
