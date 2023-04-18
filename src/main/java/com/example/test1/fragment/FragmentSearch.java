package com.example.test1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.adapter.RecycleViewAdapter;
import com.example.test1.database.Database;
import com.example.test1.model.Job;

import java.util.HashMap;
import java.util.List;

public class FragmentSearch extends Fragment {
    private SearchView searchView;
    private Spinner sp;
    private Button search, btStat;
    private TextView tvTong;
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private Database db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter=new RecycleViewAdapter();
        db = new Database(getContext());
        List<Job> list = db.getAll();
        adapter.setList(list);
        tvTong.setText("Tổng: "+list.size() +" công việc");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Job> list = db.searchJobByKey(newText);
                tvTong.setText("Tổng: "+list.size() +" công việc");
                adapter.setList(list);
                return false;
            }
        });
        btStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Integer> map = db.countJobByStatus();
                String[] status = map.keySet().toArray(new String[3]);
                String res="";
                for(String s: status){
                    if(s!=null) res += "Tổng tình trạng "+s +" là: "+ map.get(s)+"\n";
                }
                tvTong.setText(res);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Job> list = db.getByStatus(sp.getSelectedItem().toString());
                tvTong.setText("Tổng: "+list.size() +" công việc");
                adapter.setList(list);
            }
        });
    }

    private void initView(View view) {
        searchView = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycleView);
        sp = view.findViewById(R.id.spinner);
        search = view.findViewById(R.id.btnSearch);
        tvTong = view.findViewById(R.id.tvTong);
        btStat = view.findViewById(R.id.btnStat);
        sp.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.status)));
    }
}
