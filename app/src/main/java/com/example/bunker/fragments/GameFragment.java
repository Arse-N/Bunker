package com.example.bunker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.bunker.R;
import com.example.bunker.activities.TeamActivity;
import com.example.bunker.constants.RoundsData;
import com.example.bunker.service.GithubManager;

import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment {


    private TextView roundHeader, roundDesc;
    private final List<String> roundInfos = Arrays.asList(RoundsData.ROUNDS_DESC);

    private GithubManager githubManager;

    private Button next;

    private int counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        githubManager = new GithubManager(requireContext());
        next = view.findViewById(R.id.next_button);
        roundHeader = view.findViewById(R.id.round_header);
        roundDesc = view.findViewById(R.id.round_desc);
        roundHeader.setText("Ռաունդ " + 1);
        roundDesc.setText(roundInfos.get(counter));
        counter++;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < roundInfos.size()) {
                    roundHeader.setText("Ռաունդ " + (counter + 1));
                    roundDesc.setText(roundInfos.get(counter));
                } else {
                    githubManager.deleteJsonFromGitHub();
                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                if (counter == roundInfos.size() - 1) {
                    next.setText(getString(R.string.end));
                }
                counter++;
            }
        });
        return view;
    }

}
