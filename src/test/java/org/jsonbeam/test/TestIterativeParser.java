/**
 *    Copyright 2015 Sven Ewald
 *
 *    This file is part of JSONBeam.
 *
 *    JSONBeam is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, any
 *    later version.
 *
 *    JSONBeam is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with JSONBeam.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jsonbeam.test;

import java.util.Scanner;

import org.jsonbeam.index.JBQueries;
import org.jsonbeam.index.model.IndexReference;
import org.jsonbeam.io.CharacterSource;
import org.jsonbeam.io.StringCharacterSource;
import org.jsonbeam.jsonprojector.parser.IterativeJSONParser;
import org.jsonbeam.jsonprojector.parser.JSONParser;
import org.jsonbeam.jsonprojector.projector.BCJSONProjector;
import org.jsonbeam.test.examples.ActionLabel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIterativeParser {

	private final String json = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/jsonbeam/test/examples/ActionLabel.json")).useDelimiter("\\A").next();

	@Test
	public void testActionLabelParsingStaticPathResolverFast() {
		JBQueries queries = new JBQueries();
		CharacterSource source = new StringCharacterSource(json);
		IndexReference reference = JSONParser.fModelParser.apply(source, queries).createIndex();
		reference.dump(json);
	}

	@Test
	public void testBCProjector() {
		CharacterSource source = new StringCharacterSource(json);
		ActionLabel actionLabel = new BCJSONProjector().projectCharacterSource(source, ActionLabel.class);
		assertEquals("SVG Viewer", actionLabel.getHeader());
	}

	@Test
	public void testBCProjectorMulti() {
		CharacterSource source = new StringCharacterSource(json);
		ActionLabel actionLabel = new BCJSONProjector().projectCharacterSource(source, ActionLabel.class);
		assertEquals(18, actionLabel.getAllIds().size());
	}

	@Test
	public void testBCProjectorProjection() {
		CharacterSource source = new StringCharacterSource(json);
		ActionLabel actionLabel = new BCJSONProjector().projectCharacterSource(source, ActionLabel.class);
		//assertNotNull(actionLabel.getFirstItem());
		assertEquals("Open", actionLabel.getFirstItem().getId());
	}
}