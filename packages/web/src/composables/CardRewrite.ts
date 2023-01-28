import GenericItem from "./GenericItem";

export interface Card extends GenericItem {
    title: string;
    tags: string[];
    words: { [key: string]: string };
    sentences: { [key: string]: string };
    id: string;
    created_by: string;
}


/**
 * A card represents a complete study guide, or group. 
 * For now, a card is just representive of learning languages, and vocabulary
 */
export class Card implements Card {
    title: string;
    tags: string[];
    words: { [key: string]: string };
    sentences: { [key: string]: string };
    id: string;
    created_by: string;

    constructor(title?: string, tags?: string[], words?: { [key: string]: string }, sentences?: { [key: string]: string }, id?: string, created_by?: string) {
        this.title = title || "not set";
        this.tags = tags || [];
        this.words = words || {};
        this.sentences = sentences || {};
        this.id = id || "unknown";
        this.created_by = created_by || "unknown";
    }

    set addWordPairs(pairs: {[key: string]: string}) {
        if (pairs === this.words) return
        Object.assign(this.words, pairs);
    }

    set removeWordPair(key: string) {
        const entry = this.words[key]
        if (!entry) return
        delete this.words[key];
    }

    get nativeWords(): string[] {
        return Object.keys(this.words);
    }

    get foreignWords(): string[] {
        return Object.values(this.words);
    }

    get nativeSentences() {
        return Object.keys(this.sentences);
    }

    get foreignSentences() {
        return Object.values(this.sentences);
    }


}









const deez = new Card();

deez.addWordPairs = {'hey': 'balls'}, {}