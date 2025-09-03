package ir.part.app.signal.screens;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static junit.framework.Assert.assertFalse;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.hamcrest.Matcher;
import ir.part.app.signal.features.bookmark.ui.PortfolioAdapter;
import ir.part.app.signal.R;

public class CustomViewActions {

    /**
     * ViewAction which asserts that ASSET item is not in side the {@link RecyclerView}
     * adapter.
     *
     * @param assetItem - ASSET object.
     * @return {@link ViewAction} view action.
     */
    public static ViewAction verifyAssetNotInTheList(final AssetItem assetItem) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "Expected ASSET with name "
                        + assetItem.getName()
                        + " would not present in the list but it was.";
            }

            @Override
            public void perform(UiController uiController, View view) {

                boolean isExist = false;

                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                if (adapter instanceof PortfolioAdapter) {
                    int amount = adapter.getItemCount();
                    for (int i = 0; i < amount; i++) {
                        View taskItemView = recyclerView.getLayoutManager().findViewByPosition(i);
                        TextView textView = taskItemView.findViewById(R.id.tv_name);
                        if (textView != null && textView.getText() != null) {
                            if (textView.getText().toString().equals(assetItem.getName())) {
                                isExist = true;
                            }
                        }
                    }
                }
                assertFalse("Asset with name: "
                        + assetItem.getName()
                        + " is present in the list but it shouldn't", isExist);
            }
        };
    }
}