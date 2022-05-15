import { defineStore } from "pinia";

export const useAuthStore = defineStore('AuthStore', {
    state: () => ({
        user: null as Nullable<IUser>
        
    }),
    persist: true,
    actions: {
        deleteUser() {
            this.user = null;
        },
    
        setAuthedUser(user: IUser) {
            this.user = user;
        }
    }
})

export type Nullable<T> = T | null;
export interface IUser {
    name: string;
    email: string;
    auth: string;
    id: string;
} 