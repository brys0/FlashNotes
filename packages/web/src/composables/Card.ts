export type CardGroupStep = "NotStarted" | "CreatingName" | "CreatingCards" | "CreatingAnnotations";

export interface CardPairArray {
    key: string;
    value: string;
}

export default interface ICard {
    title: string;
    tags: Array<string>;
    words: Map<string, string>;
    sentences: Map<string, string>;
}

export class Card implements ICard {
    title: string;
    tags: Array<string>;
    words: Map<string, string>;
    sentences: Map<string, string>;
    constructor(title: string, tags: Array<string> = []) {
        this.title = title;
        this.tags = tags;
        this.words = new Map<string, string>();
        this.sentences = new Map<string, string>();
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