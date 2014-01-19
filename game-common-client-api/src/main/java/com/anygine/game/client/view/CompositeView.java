package com.anygine.game.client.view;

import java.util.List;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class CompositeView<T extends View> extends ViewBase implements View {

  private final Orientation orientation;
  protected final List<T> childViews;

  protected int activeChildIndex;
  
  protected CompositeView(
      String id, Width width, Height height, AlignmentX alignmentX, 
      AlignmentY alignmentY, Orientation orientation, List<T> childViews) {
    super(id, width, height, alignmentX, alignmentY);
    this.orientation = orientation;
    this.childViews = childViews;
    activeChildIndex = 0;
  }

  @Override
  public Vector2 render(RenderContext renderContext) {
    Vector2 offset = renderContext.getOffset();
    float maxContentSize = 0.0f;
    float totalChildrenSize = 0.0f;
    for (View view : childViews) {
      RenderContext childRenderContext = new RenderContext(
          renderContext.getCanvas(), offset);
      Vector2 childSize = view.render(childRenderContext);
      if (orientation == Orientation.Vertical) {
        if (width.getType() == Dimension.Type.WRAP_CONTENT) {
          if (childSize.X > maxContentSize) {
            maxContentSize = childSize.X;
          }
        }
        offset = offset.add(new Vector2(0.0f, childSize.Y));
        totalChildrenSize += childSize.Y;
      } else {
        if (height.getType() == Dimension.Type.WRAP_CONTENT) {
          if (childSize.Y > maxContentSize) {
            maxContentSize = childSize.Y;
          }
        }
        offset = offset.add(new Vector2(childSize.X, 0.0f));
        totalChildrenSize += childSize.X;
      }
    }
    if (orientation == Orientation.Vertical) {
      if (width.getType() == Dimension.Type.WRAP_CONTENT) {
        return new Vector2(maxContentSize, totalChildrenSize);
      } else {
        return new Vector2(width.getSize(parentView), totalChildrenSize);
      } 
    } else {
      if (height.getType() == Dimension.Type.WRAP_CONTENT) {
        return new Vector2(totalChildrenSize, maxContentSize);
      } else {
        return new Vector2(totalChildrenSize, height.getSize(parentView));
      } 
    }
  }

  @Override
  public ViewAction update(Input input) {
    ViewAction viewAction = childViews.get(activeChildIndex).update(input);
    if (viewAction == ViewAction.NEXT_VIEW || viewAction == ViewAction.PREVIOUS_VIEW) {
      return handleNavigation(viewAction);
    }
    return viewAction;
  }

  private ViewAction handleNavigation(ViewAction viewAction) {
    childViews.get(activeChildIndex).onInactivate();
    activeChildIndex = nextOrPreviousChildIndex(viewAction);
    if (activeChildIndex == 0 && parentView != null) {
      return viewAction;
    } else {
      childViews.get(activeChildIndex).onActivate();
      return ViewAction.NOTHING;
    }
  }

  private int nextOrPreviousChildIndex(ViewAction viewAction) {
    if (viewAction == ViewAction.NEXT_VIEW) {
      return (activeChildIndex + 1) % childViews.size();
    } else {
      return ((activeChildIndex - 1) + childViews.size()) % childViews.size();
    }
  }

  @Override
  public void onActivate() {
    childViews.get(activeChildIndex).onActivate();
  }

  // TODO: Look into this more...
  public float getHeight(SingleViewBase childView) {
    if (height.getType() == Dimension.Type.WRAP_CONTENT) {
      return childView.getContentHeight();
    } else {
      return getHeight();
    }
  }
}
