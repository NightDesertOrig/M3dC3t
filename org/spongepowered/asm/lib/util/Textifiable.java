package org.spongepowered.asm.lib.util;

import java.util.Map;
import org.spongepowered.asm.lib.Label;

public interface Textifiable {
  void textify(StringBuffer paramStringBuffer, Map<Label, String> paramMap);
}
