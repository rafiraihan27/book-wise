import { Button } from "@/components/ui/button"

export default function Home() {
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col gap-5 row-start-2 items-center sm:items-start">
        <h1
            className="relative w-[max-content] font-mono text-2xl before:absolute before:inset-0 before:animate-typewriter before:bg-white after:absolute after:inset-0 after:w-[0.125em] after:animate-caret after:bg-black">
            Hello World
        </h1>
        Kau ni ape, kau tinggal sini ke?
        <div className="flex gap-3">
          <Button>Ini budi</Button>        
          <Button variant={"secondary"}>Ini buda</Button>        
        </div>
      </main>
    </div>
  );
}
