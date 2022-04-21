package com.makichanov.catalog_app.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makichanov.catalog_app.CatalogAppContext;
import com.makichanov.catalog_app.R;
import com.makichanov.catalog_app.databinding.FragmentHomeBinding;
import com.makichanov.catalog_app.adapter.CatalogItemAdapter;
import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.provider.impl.ApiDataProvider;
import com.makichanov.catalog_app.repository.CatalogItemCollectionRepository;
import com.makichanov.catalog_app.repository.CatalogItemRepository;
import com.makichanov.catalog_app.repository.CatalogItemRepositoryObserver;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String DEFAULT_CATEGORY = "electronics";
    private CatalogAppContext catalogAppContext = CatalogAppContext.getInstance(null);
    private CatalogItemCollectionRepository repository = catalogAppContext.getCatalogItemRepository();
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private CatalogItemAdapter catalogItemAdapter;
    private ArrayAdapter<String> categoriesSpinnerAdapter;
    private ApiDataProvider provider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            Spinner spinner = container.findViewById(R.id.categoriesSpinner);
            categoriesSpinnerAdapter = new ArrayAdapter<>(container.getContext(),
                    android.R.layout.simple_spinner_item, categories);
            categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(categoriesSpinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent,
                                           View itemSelected, int selectedItemPosition, long selectedId) {
                    String selectedCategory = homeViewModel.getSelectedCategory(selectedItemPosition);
                    List<CatalogItem> catalogItems = homeViewModel.getCatalogItems(selectedCategory).getValue();
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            EditText editText = container.findViewById(R.id.searchField);
            if (editText != null) {
                editText.setOnKeyListener((view, i, keyEvent) -> {
                    if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        String text = editText.getText().toString();
                        List<CatalogItem> items = provider.getCatalogItems();
                        List<CatalogItem> requiredItems = new ArrayList<>();
                        for (CatalogItem item : items) {
                            if (item.getTitle().contains(text)) {
                                requiredItems.add(item);
                            }
                        }
                        homeViewModel.setCatalogItems(requiredItems);
                        return true;
                    }
                    return false;
                });
            }

        });

        homeViewModel.getCatalogItems(DEFAULT_CATEGORY).observe(getViewLifecycleOwner(), items -> {
            catalogItemAdapter = new CatalogItemAdapter(items);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
            binding.catalogList.setLayoutManager(layoutManager);
            binding.catalogList.setAdapter(catalogItemAdapter);
            catalogItemAdapter.notifyDataSetChanged();
            binding.indeterminateBar.setVisibility(ProgressBar.INVISIBLE);
        });

        repository.addObserver(observer);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        repository.removeObserver(observer);
    }

    private final CatalogItemRepositoryObserver observer = new CatalogItemRepositoryObserver() {

        @Override
        public void onCreate() {
            if (catalogItemAdapter != null) {
                catalogItemAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onUpdate() {
            if (catalogItemAdapter != null) {
                catalogItemAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onDelete() {
            if (catalogItemAdapter != null) {
                catalogItemAdapter.notifyDataSetChanged();
            }
        }
    };

}