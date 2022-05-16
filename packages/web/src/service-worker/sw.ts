/// <reference lib="webworker" />
declare const self: ServiceWorkerGlobalScope;

interface PeriodicBackgroundSyncEvent extends ExtendableEvent {
  tag: string;
}
import {createHandlerBoundToURL, precacheAndRoute} from 'workbox-precaching';
import {registerRoute} from 'workbox-routing';



precacheAndRoute(self.__WB_MANIFEST || []);

registerRoute(
  ({request}) => request.mode === 'navigate',
  createHandlerBoundToURL('/index.html'),
);


self.addEventListener('install', () => self.skipWaiting());
self.addEventListener('activate', () => self.clients.claim());

self.addEventListener('notificationclick', (event) => {
  event.waitUntil(self.clients.openWindow(event.notification.tag));
  event.notification.close();
});


export { self }
