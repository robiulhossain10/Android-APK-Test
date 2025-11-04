package com.robiul.firstapk.dbUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robiul.firstapk.R;
import com.robiul.firstapk.products.ProductsAdd;
import com.robiul.firstapk.products.entity.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private ProductDao productDao;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.productDao = new ProductDao(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
       return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtName.setText(product.getName());
        holder.txtEmail.setText(product.getEmail());
        holder.txtPrice.setText("Price: " + product.getPrice());
        holder.txtQuantity.setText("Quantity: " + product.getQuantity());

        if (product.getImageUri() != null && !product.getImageUri().isEmpty()){
            holder.imgProduct.setImageURI(Uri.parse(product.getImageUri()));
        }else {
            holder.imgProduct.setImageResource(R.drawable.tt);
        }

        //Delete Button

        holder.btnDelete.setOnClickListener(v->{
            int rows = productDao.delete(product.getId());
            if (rows > 0){
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
                Toast.makeText(context,"Product Deleted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"Delete Failed", Toast.LENGTH_SHORT).show();
            }
        });

        //Edit Button
        holder.btnEdit.setOnClickListener(v->{
            Intent intent = new Intent(context, ProductsAdd.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
       return productList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtEmail,txtPrice, txtQuantity;
        ImageView imgProduct;
        ImageButton btnDelete,btnEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
