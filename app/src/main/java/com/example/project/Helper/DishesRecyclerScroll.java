package com.example.project.Helper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class DishesRecyclerScroll extends RecyclerView.OnScrollListener {

    private static final float MINIMUM = 150;
    private int scrollDist = 100;
    private boolean isVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (isVisible && scrollDist > MINIMUM) {
            hideSearchParams();
            scrollDist = 100;
            isVisible = false;
        }
        else if (!isVisible && scrollDist < -MINIMUM) {
            showSearchParams();
            scrollDist = 100;
            isVisible = true;
        }

        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }
    }

    public abstract void showSearchParams();
    public abstract void hideSearchParams();
}
