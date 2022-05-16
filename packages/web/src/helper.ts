export function createMeta(title?: string, meta?: MetaTag[]) {
    if (title) {
        document.title = title;
    }
    if (meta) {
        for (let i = 0; meta.length > i; i++) {
            let currentMeta = meta[i];
            let tag = (document.querySelector(`meta[property="${currentMeta.property}"]`) == null) ? document.createElement('meta') : document.querySelector(`meta[property='${currentMeta.property}']`) as HTMLMetaElement;
            // @ts-ignore
            tag.setAttribute('property', currentMeta.property)
            tag.content = currentMeta.content;
            document.head.appendChild(tag);
        }
    }
}

export const debounce = (fn: Function, ms = 300) => {
    let timeoutId: ReturnType<typeof setTimeout>;
    return function (this: any, ...args: any[]) {
      clearTimeout(timeoutId);
      timeoutId = setTimeout(() => fn.apply(this, args), ms);
    };
  };

export interface MetaTag {
    property: string;
    content: string;
}