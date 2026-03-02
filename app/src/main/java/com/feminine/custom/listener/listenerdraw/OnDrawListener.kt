package com.feminine.custom.listener.listenerdraw

import com.feminine.custom.Draw
import com.feminine.custom.DrawableDraw


interface OnDrawListener {
    fun onAddedDraw(draw: Draw)

    fun onClickedDraw(draw: Draw)

    fun onDeletedDraw(draw: Draw)

    fun onDragFinishedDraw(draw: Draw)

    fun onTouchedDownDraw(draw: Draw)

    fun onZoomFinishedDraw(draw: Draw)

    fun onFlippedDraw(draw: Draw)

    fun onDoubleTappedDraw(draw: Draw)

    fun onHideOptionIconDraw()

    fun onUndoDeleteDraw(draw: List<Draw?>)

    fun onUndoUpdateDraw(draw: List<Draw?>)

    fun onUndoDeleteAll()

    fun onRedoAll()

    fun onReplaceDraw(draw: Draw)

    fun onEditText(draw: DrawableDraw)

    fun onReplace(draw: Draw)
}