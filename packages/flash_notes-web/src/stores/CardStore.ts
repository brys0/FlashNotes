import { defineStore } from "pinia";
import { Card } from "../composables/Card";
export const useCardStore = defineStore('CardStore', {
    state: () => {
        return {
            tags: new Array<String>(),
            cards: new Map<string, Card>(),
        }
    },
    actions: {
        createCard(title: string, tags: string[]) {
            console.log('Cards', this.cards);
            this.cards.set(title, new Card(title, tags));
        },
        async getAllTags() {
           let res = await fetch('/tags');
           let json = await res.json();
           console.log(res, json);
           this.tags = json;
        }
    }
})