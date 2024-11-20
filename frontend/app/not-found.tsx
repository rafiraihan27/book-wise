import { Button } from '@/components/ui/button';
import Link from 'next/link';

export default function NotFound() {
  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100 text-gray-900">
      <h1 className="text-5xl font-bold">404</h1>
      <p className="text-lg mt-4">Page Not Found</p>
      <Button className='mt-10'>
        <Link href="/">Go back to hone</Link>
      </Button>
    </div>
  );
}
