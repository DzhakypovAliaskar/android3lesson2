package com.example.android3lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android3lesson2.R;
import com.example.android3lesson2.data.models.Post;
import com.example.android3lesson2.databinding.FragmentFormBinding;
import com.example.android3lesson2.utils.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private static final int USER_ID = 6;
    private static final int GROUP_ID = 6;
    private Post postTwo;

    public FormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            postTwo = (Post) getArguments().getSerializable("ttt");
            binding.etTitle.setText(postTwo.getTitle());
            binding.etContent.setText(postTwo.getContent());
        }

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postTwo == null) {
                    String title = binding.etTitle.getText().toString();
                    String content = binding.etContent.getText().toString();
                    Post post = new Post(
                            title,
                            content,
                            USER_ID,
                            GROUP_ID
                    );
                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            if (response.isSuccessful()) {
                                NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                                controller.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                        }
                    });
                } else {
                    String title = binding.etTitle.getText().toString();
                    String content = binding.etContent.getText().toString();
                    Post post = new Post(title, content, USER_ID, GROUP_ID);
                    App.api.updatePost(postTwo.getId(), post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            Toast.makeText(requireActivity(), "ОБНОВЛЕНО", Toast.LENGTH_SHORT).show();
                            NavController controller = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                            controller.navigate(R.id.postsFragment);
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}