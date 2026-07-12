package com.onyx.android.sdk.data.dict;

import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.List;

public class DictionaryQueryResult {
    public String originWord;
    public String candidate;
    public String explanation;
    public DictionaryInfo dictionary;
    public int entryIndex;
    public String linkToWord;
    public List<String> soundPathList;

    public void addSoundPath(String soundPath) {
        List<String> listEnsureList = CollectionUtils.ensureList(this.soundPathList);
        this.soundPathList = listEnsureList;
        if (listEnsureList.contains(soundPath)) {
            return;
        }
        this.soundPathList.add(soundPath);
    }

    public boolean equals(Object object) {
        DictionaryInfo dictionaryInfo;
        DictionaryInfo dictionaryInfo2;
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof DictionaryQueryResult)) {
            return false;
        }
        DictionaryQueryResult dictionaryQueryResult = (DictionaryQueryResult) object;
        String str = this.explanation;
        if (str == null && dictionaryQueryResult.explanation == null && (dictionaryInfo = this.dictionary) == null && (dictionaryInfo2 = dictionaryQueryResult.dictionary) == null && dictionaryInfo.dictVoiceInfo == null && dictionaryInfo2.dictVoiceInfo == null) {
            return true;
        }
        return str != null && dictionaryQueryResult.explanation != null && this.candidate.equals(dictionaryQueryResult.candidate) && this.explanation.equals(dictionaryQueryResult.explanation);
    }

    public int hashCode() {
        return this.candidate.hashCode();
    }

    @NonNull
    public DictionaryQueryResult clone() {
        DictionaryQueryResult dictionaryQueryResult = new DictionaryQueryResult();
        dictionaryQueryResult.originWord = this.originWord;
        dictionaryQueryResult.candidate = this.candidate;
        dictionaryQueryResult.dictionary = this.dictionary;
        dictionaryQueryResult.entryIndex = this.entryIndex;
        dictionaryQueryResult.linkToWord = this.linkToWord;
        dictionaryQueryResult.explanation = this.explanation;
        dictionaryQueryResult.soundPathList = this.soundPathList;
        return dictionaryQueryResult;
    }
}
