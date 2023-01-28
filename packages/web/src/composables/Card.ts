export type CardGroupStep = "NotStarted" | "CreatingName" | "CreatingCards" | "CreatingAnnotations";
export type CardGroupSearchType = "Tag" | "Title";
export interface CardPairArray {
    key: string;
    value: string;
}

export interface ICardSearch {
    value: string;
    type: CardGroupSearchType
}

export interface ICard {
    title: string;
    tags: Array<string>;
    words: Map<string, string>;
    sentences: Map<string, string>;
    id: string;
    created_by: string;
}
export interface ISentenceWithWords {
    struct: KeyValue<string, string>,
    words: Map<string, string>
}
export interface KeyValue<K, V> {
    key: K,
    value: V
}
export class Card implements ICard {
    title: string;
    tags: Array<string>;
    words: Map<string, string>;
    sentences: Map<string, string>;
    id: string;
    created_by: string;
    constructor(title: string, id: string, created_by: string, tags: Array<string> = []) {
        this.title = title;
        this.tags = tags;
        this.words = new Map<string, string>();
        this.sentences = new Map<string, string>();
        this.id = id;
        this.created_by = created_by;
    }

    async save(auth: string, existingId: string | null) {
        let serializedCard = {
            title: this.title,
            tags: this.tags,
            words: Object.fromEntries(this.words),
            sentences: Object.fromEntries(this.sentences)
        };
        return await fetch(`/create${existingId ? '?groupid=' + existingId : ''}`, {
            method: 'POST',
            headers: {
                Authorization: auth
            },
            body: JSON.stringify(serializedCard)
        })
    }
}