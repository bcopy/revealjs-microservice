package com.github.cmcrobotics.reveal.process;

import com.github.cmcrobotics.reveal.model.Cursor;

public interface Processor<T>{
    @SuppressWarnings("unchecked")
	Cursor process(Cursor cursor, T... arg);
}
