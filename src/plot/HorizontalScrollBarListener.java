package plot;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

class HorizontalScrollBarListener implements AdjustmentListener {

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (Model.horScrollBar.getValueIsAdjusting())
			return;
		int curValue = e.getValue();
		Model.plot.horizontalShift(Model.horScrollValue - curValue);
		Model.horScrollValue = curValue;
	}
}
