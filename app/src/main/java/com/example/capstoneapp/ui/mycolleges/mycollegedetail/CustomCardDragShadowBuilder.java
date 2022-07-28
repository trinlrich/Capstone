package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.capstoneapp.R;

public class CustomCardDragShadowBuilder extends View.DragShadowBuilder {

    // The drag shadow image, defined as a drawable object.
    private static Drawable shadow;

    // Constructor
    public CustomCardDragShadowBuilder(View v) {

        // Stores the View parameter.
        super(v);

        // Creates a draggable image that fills the Canvas provided by the system.
        shadow = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_task);
    }

    // Defines a callback that sends the drag shadow dimensions and touch point
    // back to the system.
    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        // Defines local variables
        int width, height;

        // Set the width of the shadow to half the width of the original View.
        width = getView().getHeight() / 2;

        // Set the height of the shadow to half the height of the original View.
        height = getView().getHeight() / 2;

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the
        // same as the Canvas that the system provides. As a result, the drag shadow
        // fills the Canvas.
        shadow.setBounds(0, 0, width, height);

        // Set the size parameter's width and height values. These get back to the
        // system through the size parameter.
        outShadowSize.set(width, height);

        // Set the touch point's position to be in the middle of the drag shadow.
        outShadowTouchPoint.set(width / 2, height / 2);
    }


    // Defines a callback that draws the drag shadow in a Canvas that the system
    // constructs from the dimensions passed to onProvideShadowMetrics().


    @Override
    public void onDrawShadow(Canvas canvas) {
        // Draw the ColorDrawable on the Canvas passed in from the system.
        shadow.draw(canvas);
    }

}
