import { defineStore } from "pinia";
import { ICard, Card, CardGroupSearchType } from "../composables/Card";
import { Nullable, useAuthStore } from "./AuthStore";
export const useCardStore = defineStore('CardStore', {
    state: () => {
        return {
            tags: new Array<String>(),
            cards: new Map<string, Card>(),

            currentCard: null as Nullable<Card>,
            searchCards: new Map<string, Card>()
        }
    },
    actions: {
        createCard(title: string, id: string, created_by: string, tags: string[]) {
            console.log('Cards', this.cards);
            this.cards.set(title, new Card(title, id, created_by, tags));
        },
        async getAllTags() {
            let res = await fetch('/tags');
            let json = await res.json();
            console.log(res, json);
            this.tags = json;
        },

        async browseCards() {
            let res = await fetch('/browse');
            let json = await res.json();
            this.searchCards.clear();
            console.log(res, json);
            for (let i = 0; json.length > i; i++) {
                let interfacedCard = json[i];
                let card = new Card(interfacedCard.title, interfacedCard.id, interfacedCard.created_by, interfacedCard.tags);
                // let cardWordEntries = Object.entries(interfacedCard.words);
                // let cardSentenceEntries = Object.entries(interfacedCard.sentences);
                // for (let we = 0; cardWordEntries.length > we; we++) {
                //     let wordKeyPair = cardWordEntries[we];
                //     let key = wordKeyPair[0] as string;
                //     let value = wordKeyPair[1] as string;
                //     card.words.set(key, value);
                // }
                // for (let se = 0; cardSentenceEntries.length > se; se++) {
                //     let sentenceKeyPair = cardSentenceEntries[se];
                //     let key = sentenceKeyPair[0] as string;
                //     let value = sentenceKeyPair[1] as string;
                //     card.sentences.set(key, value);
                // }

                this.searchCards.set(card.id, card);
            }
        },
        async getMyGroups() {
            let auth = useAuthStore();
            if (!auth.user) throw SyntaxError("Missing user to complete this request");
            let res = await fetch('/mygroups', {
                headers: {
                    Authorization: auth.user.auth
                }
            });
            if (res.status == 204) return
            let json = await res.json();
            for (let i = 0; json.length > i; i++) {
                let interfacedCard = json[i];
                let card = new Card(interfacedCard.title, interfacedCard.id, interfacedCard.created_by, interfacedCard.tags);
                let cardWordEntries = Object.entries(interfacedCard.words);
                let cardSentenceEntries = Object.entries(interfacedCard.sentences);
                for (let we = 0; cardWordEntries.length > we; we++) {
                    let wordKeyPair = cardWordEntries[we];
                    let key = wordKeyPair[0] as string;
                    let value = wordKeyPair[1] as string;
                    card.words.set(key, value);
                }
                for (let se = 0; cardSentenceEntries.length > se; se++) {
                    let sentenceKeyPair = cardSentenceEntries[se];
                    let key = sentenceKeyPair[0] as string;
                    let value = sentenceKeyPair[1] as string;
                    card.sentences.set(key, value);
                }
                this.cards.set(card.id, card);
            }
        },
        async searchForCards(filterBy: CardGroupSearchType, value: string) {
            let res = await fetch(`/filter?filterBy=${filterBy}&value=${value}`);
            let json = await res.json();
            this.searchCards.clear();
            for (let i = 0; json.length > i; i++) {
                let interfacedCard = json[i];
                let card = new Card(interfacedCard.title, interfacedCard.id, interfacedCard.created_by, interfacedCard.tags);
                // let cardWordEntries = Object.entries(interfacedCard.words);
                // let cardSentenceEntries = Object.entries(interfacedCard.sentences);
                // for (let we = 0; cardWordEntries.length > we; we++) {
                //     let wordKeyPair = cardWordEntries[we];
                //     let key = wordKeyPair[0] as string;
                //     let value = wordKeyPair[1] as string;
                //     card.words.set(key, value);
                // }
                // for (let se = 0; cardSentenceEntries.length > se; se++) {
                //     let sentenceKeyPair = cardSentenceEntries[se];
                //     let key = sentenceKeyPair[0] as string;
                //     let value = sentenceKeyPair[1] as string;
                //     card.sentences.set(key, value);
                // }

                this.searchCards.set(card.id, card);
            }
        },
        
        async getCard(id: string) {
            let res = await fetch(`/card?id=${id}`);
            let interfacedCard = await res.json();
            let cardWordEntries = Object.entries(interfacedCard.words);
            let cardSentenceEntries = Object.entries(interfacedCard.sentences);
            let card = new Card(interfacedCard.title, interfacedCard.id, interfacedCard.created_by, interfacedCard.tags);
            for (let we = 0; cardWordEntries.length > we; we++) {
                let wordKeyPair = cardWordEntries[we];
                let key = wordKeyPair[0] as string;
                let value = wordKeyPair[1] as string;
                card.words.set(key, value);
            }
            for (let se = 0; cardSentenceEntries.length > se; se++) {
                let sentenceKeyPair = cardSentenceEntries[se];
                let key = sentenceKeyPair[0] as string;
                let value = sentenceKeyPair[1] as string;
                card.sentences.set(key, value);
            }
            this.currentCard = card;
        }
    }
})