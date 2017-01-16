package com.github.who_you_me.deeplearning.layer

import breeze.linalg.DenseMatrix

class Relu extends Layer {
  private var mask: Option[DenseMatrix[Boolean]] = None

  override def forward(x: DenseMatrix[Double]) = {
    mask = Some(x.map(_ <= 0))
    x.map(xx => if (xx > 0) xx else 0.0)
  }

  override def backward(dout: DenseMatrix[Double]) = {
    assert(this.mask.nonEmpty)
    val mask = this.mask.get

    assert((dout.rows, dout.cols) == (mask.rows, mask.cols))

    for (i <- 0 until dout.rows; j <- 0 until dout.cols) {
      if (mask(i, j)) dout.update(i, j, 0.0)
    }
    dout
  }
}
