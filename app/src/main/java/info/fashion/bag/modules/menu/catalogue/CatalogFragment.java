package info.fashion.bag.modules.menu.catalogue;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.fashion.bag.R;

/**
 * Created by gtufinof on 3/11/18.
 */

public class CatalogFragment extends Fragment {

    private String TAG = CatalogFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    public static CatalogFragment newInstance(){
        return new CatalogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ctx = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Catálogo");
    }

    @Override
    public void onStart() {
        super.onStart();
        callService();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void callService(){

    }

}
