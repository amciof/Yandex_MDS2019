package com.amciof.Yandex_MDS2019.Activities.ListOfApplications;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amciof.Yandex_MDS2019.R;
import com.amciof.Yandex_MDS2019.SPHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class FragmentList
        extends Fragment {

    private GetInstalledApplications gia;

    private List<ApplicationInfo> applicationList = new ArrayList<>();
    private ApplicationAdapter adapter;
    private RecyclerView appsRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetInstalledApplications) {
            gia = (GetInstalledApplications) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        applicationList.addAll(gia.getInstalledApplications());

        appsRecyclerView = rootView.findViewById(R.id.apps_recycler);
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        appsRecyclerView.setHasFixedSize(true);
        appsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        appsRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));

        adapter = new ApplicationAdapter(rootView.getContext(), applicationList, R.layout.item_list);
        appsRecyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String comparator = SPHelper.getString(getContext(), SPHelper.SORT, SPHelper.ASCENDING_BY_LABEL);

        switch (comparator) {
            case SPHelper.ASCENDING_BY_LABEL:
                Collections.sort(applicationList, ApplicationInfo.ASCENDING_BY_LABEL);
                break;
            case SPHelper.ASCENDING_BY_DATE :
                Collections.sort(applicationList, ApplicationInfo.ASCENDING_BY_DATE);
                break;
            case SPHelper.ASCENDING_BY_LAUNCHES:
                Collections.sort(applicationList, ApplicationInfo.ASCENDING_BY_LAUNCHES);
                break;
            case SPHelper.DESCENDING_BY_LABEL:
                Collections.sort(applicationList, ApplicationInfo.DESCENDING_BY_LABEL);
                break;
            case SPHelper.DESCENDING_BY_DATE:
                Collections.sort(applicationList, ApplicationInfo.DESCENDING_BY_DATE);
                break;
            case SPHelper.DESCENDING_BY_LAUNCHES:
                Collections.sort(applicationList, ApplicationInfo.DESCENDING_BY_LAUNCHES);
                break;
        }

        adapter.notifyDataSetChanged();
    }

}
