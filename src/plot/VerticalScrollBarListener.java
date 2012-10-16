package plot;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class VerticalScrollBarListener implements AdjustmentListener {

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (Model.vertScrollBar.getValueIsAdjusting())
			return;
		int curValue = e.getValue();
		Model.plot.verticalShift(curValue - Model.vertScrollValue);
		Model.vertScrollValue = curValue;
	}
}
