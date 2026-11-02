package com.sun.activation.viewers;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/* loaded from: classes2.dex */
public class ImageViewerCanvas extends Canvas {
    private Image canvas_image = null;

    public void setImage(Image image) {
        this.canvas_image = image;
        invalidate();
        repaint();
    }

    public Dimension getPreferredSize() {
        if (this.canvas_image == null) {
            return new Dimension(200, 200);
        }
        return new Dimension(this.canvas_image.getWidth(this), this.canvas_image.getHeight(this));
    }

    public void paint(Graphics graphics) {
        Image image = this.canvas_image;
        if (image != null) {
            graphics.drawImage(image, 0, 0, this);
        }
    }
}
