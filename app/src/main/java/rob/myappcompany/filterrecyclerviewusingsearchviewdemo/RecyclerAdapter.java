package rob.myappcompany.filterrecyclerviewusingsearchviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {


    private static final String TAG = "Recycler Adapter";
    List<String> movieList;
    List<String> movieListAll;
    public RecyclerAdapter(List<String> movieList)
    {
        this.movieList = movieList;
        this.movieListAll = new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.rowTextView.setText(String.valueOf(position));
        holder.textView.setText(movieList.get(position));

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {


        return filter;
    }

    Filter filter = new Filter() {
        //run on Background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
                  List<String> filterList = new ArrayList<>();
                  if (charSequence.toString().isEmpty()){
                      filterList.addAll(movieListAll);
                  }else {
                      for (String movie : movieListAll){
                          if (movie.toLowerCase().contains(charSequence.toString().toLowerCase(Locale.ROOT))){
                              filterList.add(movie);
                          }
                      }
                  }
                  FilterResults filterResults = new FilterResults();
                  filterResults.values = filterList;

            return filterResults;
        }

        //run on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            movieList.clear();
            movieList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };




    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView, rowTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            rowTextView = itemView.findViewById(R.id.rowTextView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    movieList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), " "+ movieList.get(getAdapterPosition()), Toast.LENGTH_LONG).show();
        }
    }
}
