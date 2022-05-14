import { defineStore } from "pinia";

export const useCardStore = () => defineStore('CardStore', {
    state: () => {
        return {
            cards: []
        }
    }
})