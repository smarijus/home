package lt.smart.home.home;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import lt.smart.home.ApplicationApp;
import lt.smart.home.R;
import lt.smart.home.base.BaseViewController;

public class HomeController extends BaseViewController implements IHomeView {

    @BindView(R.id.send)
    Button sendView;

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.message)
    EditText messageView;

    @BindView(R.id.turnOn)
    Button turnOn;

    @BindView(R.id.turnOff)
    Button turnOff;

    @Inject
    HomePresenter homePresenter;

    private TcpClient mTcpClient;
    private ArrayAdapter adapter;

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        if (adapter == null) {
            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList());
            listView.setAdapter(adapter);
        }
        turnOn.setOnClickListener(v -> homePresenter.turnLight(true));
        turnOff.setOnClickListener(v -> homePresenter.turnLight(false));
        sendView.setOnClickListener(v -> {
            if (mTcpClient != null) {
                mTcpClient.sendMessage(messageView.getText().toString());
            }
        });
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ApplicationApp.getAppComponent(getActivity()).inject(this);
        return inflater.inflate(R.layout.controller_home, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        homePresenter.setView(this);
        new ConnectTask().execute("");
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        homePresenter.setView(null);
        if (mTcpClient != null) {
            mTcpClient.stopClient();
        }
    }

    class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....
            adapter.add(values[0]);
        }
    }
}
