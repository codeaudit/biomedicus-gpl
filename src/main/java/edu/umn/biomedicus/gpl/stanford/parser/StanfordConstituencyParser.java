/*
 * Copyright (C) 2016 Regents of the University of Minnesota
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.umn.biomedicus.gpl.stanford.parser;

import edu.umn.biomedicus.application.DocumentProcessor;
import edu.umn.biomedicus.common.labels.Label;
import edu.umn.biomedicus.common.labels.LabelIndex;
import edu.umn.biomedicus.common.labels.Labeler;
import edu.umn.biomedicus.common.types.syntax.PartOfSpeech;
import edu.umn.biomedicus.common.types.text.ConstituencyParse;
import edu.umn.biomedicus.common.types.text.Document;
import edu.umn.biomedicus.common.types.text.ParseToken;
import edu.umn.biomedicus.common.types.text.Sentence;
import edu.umn.biomedicus.exc.BiomedicusException;

import javax.inject.Inject;

public class StanfordConstituencyParser implements DocumentProcessor {
    private final LabelIndex<Sentence> sentenceLabelIndex;
    private final LabelIndex<PartOfSpeech> partOfSpeechLabelIndex;
    private final Labeler<ConstituencyParse> constituencyParseLabeler;
    private final LabelIndex<ParseToken> parseTokenLabelIndex;
    private final StanfordConstituencyParserModel stanfordConstituencyParserModel;

    @Inject
    public StanfordConstituencyParser(Document document,
                                      StanfordConstituencyParserModel stanfordConstituencyParserModel) {
        sentenceLabelIndex = document.getLabelIndex(Sentence.class);
        parseTokenLabelIndex = document.getLabelIndex(ParseToken.class);
        partOfSpeechLabelIndex = document.getLabelIndex(PartOfSpeech.class);
        constituencyParseLabeler = document.getLabeler(ConstituencyParse.class);
        this.stanfordConstituencyParserModel = stanfordConstituencyParserModel;
    }

    @Override
    public void process() throws BiomedicusException {
        for (Label<Sentence> sentenceLabel : sentenceLabelIndex) {
            stanfordConstituencyParserModel.parseSentence(sentenceLabel, parseTokenLabelIndex, partOfSpeechLabelIndex,
                    constituencyParseLabeler);
        }
    }
}
