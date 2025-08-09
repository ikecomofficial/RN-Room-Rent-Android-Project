package com.example.rnroomrent.ui.account_setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rnroomrent.LoginScreenActivity;
import com.example.rnroomrent.R;
import com.example.rnroomrent.databinding.FragmentAccountSettingBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSettingFragment extends Fragment {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private FragmentAccountSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountSettingViewModel accountSettingViewModel =
                new ViewModelProvider(this).get(AccountSettingViewModel.class);

        binding = FragmentAccountSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textDisplayName = binding.textDisplayName;
        TextView textDisplayEmail = binding.textDisplayEmail;

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){

            // Configure Google Sign In to get the client for sign-out
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

            textDisplayName.setText(user.getDisplayName());
            textDisplayEmail.setText(user.getEmail());

            // Taking url of Google Signed In user Profile Pic
            String originalProfileUrl = user.getPhotoUrl().toString();

            // Replace the size suffix (e.g., =s96-c) with your own
            String thumbProfileUrl = originalProfileUrl.replaceAll("=s\\d+-c", "=s200-c");
            Glide.with(requireContext())
                    .load(thumbProfileUrl)
                    .placeholder(R.drawable.placeholder_account_setting_profile_image)
                    .into(binding.accSettingProfileImage);
            binding.textSignOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut();
                }
            });
        }

        return root;
    }

    private void signOut() {
        // First, sign out from Firebase
        mAuth.signOut();

        // Next, sign out from Google
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            Toast.makeText(requireContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
            goToLoginScreen(); // Redirect to the login screen after successful sign out
        });
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(requireContext(), LoginScreenActivity.class);
        startActivity(intent);
        requireActivity().finish(); // End MainActivity so the user can't press back to it
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}