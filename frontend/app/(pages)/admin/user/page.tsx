"use client"

import { useEffect, useState } from "react"
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area"
import { FilePlus2, FilePenLine, Trash2 } from 'lucide-react'
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { toast } from "sonner"
import { Textarea } from "@/components/ui/textarea"
import { Separator } from "@/components/ui/separator"
import { Badge } from "@/components/ui/badge"
import { User } from "@/types/interfaces"
import { deleteUserById, fetchAllUsers, registerAdmin, registerLecturer, registerStudent, updateUserById } from "@/lib/api/users"

export default function UsersPage() {
    const [users, setUsers] = useState<User[]>([]);
    const [search, setSearch] = useState("");
    const [editingUser, setEditingUser] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string>("");

    // Fetch Users
    useEffect(() => {
        async function loadUsers() {
            setLoading(true);
            try {
                const data = await fetchAllUsers();
                setUsers(data);
                setError("");
            } catch (err) {
                setError(`Failed to load books: ${err}`);
            } finally {
                setLoading(false);
            }
        }

        loadUsers();
    }, []);

    if (loading) {
        return <div className="loading">Tunggu bentar, data user lagi diambil dari database...</div>;
    }

    if (error) {
        return <div className="error">{error}</div>;
    }

    const filteredUsers = users.filter((user) => {
        const matchesSearch =
            (user.id?.toLowerCase().includes(search.toLowerCase()) ?? false) ||
            (user.name?.toLowerCase().includes(search.toLowerCase()) ?? false) ||
            (user.email?.toLowerCase().includes(search.toLowerCase()) ?? false) ||
            (user.phone?.toLowerCase().includes(search.toLowerCase()) ?? false);
        return matchesSearch;
    });

    // Handle Add User
    const handleAddUser = async (newUser: Omit<User, "id">) => {
        try {
            if (newUser.role == "student") {
                const addedUser = await registerStudent(newUser);
                setUsers([...users, addedUser]);
            } else if (newUser.role == "lecturer") {
                const addedUser = await registerLecturer(newUser);
                setUsers([...users, addedUser]);
            } else if (newUser.role == "admin") {
                const addedUser = await registerAdmin(newUser);
                setUsers([...users, addedUser]);
            }

            toast.success("User Added", {
                description: `${newUser.name} has been added successfully as ${newUser.role}.`,
            });
        } catch (err) {
            toast.error("Failed to add user", { description: `${err}` });
        }
    };

    // Handle Edit User
    const handleEditUser = async (editedUser: User) => {
        try {
            const updatedUser = await updateUserById(editedUser.id || "", editedUser);
            setUsers((prevUsers) =>
                prevUsers.map((book) => (book.id === updatedUser.id ? updatedUser : book))
            );
            toast.success("User Updated", {
                description: `${updatedUser.title} has been updated successfully.`,
            });
        } catch (err) {
            toast.error("Failed to update user", { description: `${err}` });
        }
    };

    // Handle Delete User
    const handleDeleteUser = async (id: string) => {
        try {
            await deleteUserById(id);
            setUsers(users.filter((user) => user.id !== id));
            toast.error("User Deleted", {
                description: "The User has been deleted successfully.",
            });
        } catch (err) {
            toast.error("Failed to delete user", { description: `${err}` });
        }
    };

    return (
        <div className="flex flex-col h-full">
            <div className="space-y-6 mb-6">
                <h1 className="text-3xl font-bold">Users Management</h1>
                <div className="flex flex-row justify-center items-end gap-4">
                    <div className="flex-1">
                        <Label htmlFor="search">Search</Label>
                        <Input
                            id="search"
                            placeholder="Search user here..."
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                        />
                    </div>
                    <div>
                        <Dialog>
                            <DialogTrigger asChild>
                                <Button>
                                    <FilePlus2 className="mr-2 h-4 w-4" />
                                    Add User
                                </Button>
                            </DialogTrigger>
                            <DialogContent className="max-w-xl w-full overflow-y-auto">
                                <DialogHeader>
                                    <DialogTitle>Add New User</DialogTitle>
                                </DialogHeader>
                                <ScrollArea className="max-h-[70vh] overflow-y-auto">
                                    <UserForm onSubmit={handleAddUser} />
                                    <ScrollBar />
                                </ScrollArea>
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>
            </div>
            <div className="flex flex-col md:flex-row gap-4">
                {["student", "lecturer", "admin"].map((item) => (
                    <div className="flex-grow overflow-auto">
                        <Badge className="uppercase mb-3">{item}</Badge>
                        <div className="border rounded-md">
                            <ScrollArea className="h-full w-full">
                                <div className="w-full min-w-max">
                                    <Table>
                                        <TableCaption>A list of {item} in the library.</TableCaption>
                                        <TableHeader>
                                            <TableRow>
                                                <TableHead className="w-[40px]">ID</TableHead>
                                                <TableHead>Name</TableHead>
                                                <TableHead>Email</TableHead>
                                                <TableHead>Phone</TableHead>
                                                <TableHead className="text-center">Actions</TableHead>
                                            </TableRow>
                                        </TableHeader>
                                        <TableBody>
                                            {filteredUsers.filter((u) => u.role === item).map((user) => (
                                                <TableRow key={user.id}>
                                                    <TableCell className="font-medium">{user.id}</TableCell>
                                                    <TableCell>{user.name}</TableCell>
                                                    <TableCell>{user.email}</TableCell>
                                                    <TableCell>{user.phone}</TableCell>
                                                    <TableCell className="text-center">
                                                        <Dialog>
                                                            <DialogTrigger asChild>
                                                                <Button variant="ghost" size="icon" onClick={() => setEditingUser(user)}>
                                                                    <FilePenLine className="h-4 w-4" />
                                                                </Button>
                                                            </DialogTrigger>
                                                            <DialogContent className="max-w-4xl w-full overflow-y-auto">
                                                                <DialogHeader>
                                                                    <DialogTitle>Edit user [{item}]</DialogTitle>
                                                                </DialogHeader>
                                                                {editingUser && (
                                                                    <ScrollArea className="max-h-[70vh] overflow-y-auto">
                                                                        <UserForm user={editingUser} onSubmit={handleEditUser} />
                                                                        <ScrollBar />
                                                                    </ScrollArea>
                                                                )}
                                                            </DialogContent>
                                                        </Dialog>
                                                        <Button variant="ghost" size="icon" onClick={() => handleDeleteUser(user.id || "")}>
                                                            <Trash2 className="h-4 w-4" />
                                                        </Button>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </div>
                                <ScrollBar orientation="horizontal" />
                            </ScrollArea>
                        </div>
                    </div>
                ))}
            </div>
            {filteredUsers.length === 0 && (
                <p className="text-center text-muted-foreground mt-4">No user found.</p>
            )}
        </div>
    )
}

interface UserFormProps {
    user?: User
    onSubmit: (user: User | Omit<User, 'id'>) => void
}

import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

function UserForm({ user, onSubmit }: UserFormProps) {
    const [formData, setFormData] = useState<Omit<User, "id"> | User>({
        id: "",
        email: "",
        password: "",
        name: "",
        phone: "",
        role: "student",
        nim: "",
        nip: "",
        year: "",
        ...(user || {}),
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: name === "year" || name === "quota" || name === "availableCopies" || name === "lateFee"
                ? parseInt(value, 10) || 0
                : value,
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(formData);
    };

    // Update formData when tab is selected
    const handleTabChange = (selectedTab: string) => {
        setFormData((prevData) => ({
            ...prevData,
            role: selectedTab as "student" | "lecturer" | "admin",
        }));
    };

    return (
        <>
            {!user && (
                <Tabs defaultValue="student" className="w-full" onValueChange={handleTabChange}>
                    <TabsList className="grid w-full grid-cols-3 mb-6">
                        <TabsTrigger value="student">Student</TabsTrigger>
                        <TabsTrigger value="lecturer">Lecturer</TabsTrigger>
                        <TabsTrigger value="admin">Admin</TabsTrigger>
                    </TabsList>
                    <TabsContent value="student">
                        <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                            <div className="space-y-4">
                                <div>
                                    <Label htmlFor="name">Name</Label>
                                    <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                                </div>
                                <div className="grid grid-cols-3 gap-3">
                                    <div className="md:col-span-2">
                                        <Label htmlFor="email">Email</Label>
                                        <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="phone">Phone</Label>
                                        <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="password">Password</Label>
                                        <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="nim">NIM</Label>
                                        <Input id="nim" name="nim" value={formData.nim} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="year">year</Label>
                                        <Input id="year" name="year" value={formData.year} onChange={handleChange} required />
                                    </div>
                                </div>
                            </div>

                            <Button type="submit" className="md:col-span-2 mt-5">
                                Submit
                            </Button>
                        </form>
                    </TabsContent>
                    <TabsContent value="lecturer">
                        <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                            <div className="space-y-4">
                                <div>
                                    <Label htmlFor="name">Name</Label>
                                    <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                                </div>
                                <div className="grid grid-cols-3 gap-3">
                                    <div className="md:col-span-2">
                                        <Label htmlFor="email">Email</Label>
                                        <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="phone">Phone</Label>
                                        <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="password">Password</Label>
                                        <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                                    </div>
                                    <div className="md:col-span-2">
                                        <Label htmlFor="nip">NIP</Label>
                                        <Input id="nip" name="nip" value={formData.nip} onChange={handleChange} required />
                                    </div>
                                </div>
                            </div>

                            <Button type="submit" className="md:col-span-2 mt-5">
                                Submit
                            </Button>
                        </form>
                    </TabsContent>
                    <TabsContent value="admin">
                        <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                            <div className="space-y-4">
                                <div>
                                    <Label htmlFor="name">Name</Label>
                                    <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                                </div>
                                <div className="grid grid-cols-3 gap-3">
                                    <div className="md:col-span-2">
                                        <Label htmlFor="email">Email</Label>
                                        <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="phone">Phone</Label>
                                        <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                                    </div>
                                    <div>
                                        <Label htmlFor="password">Password</Label>
                                        <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                                    </div>
                                </div>
                            </div>

                            <Button type="submit" className="md:col-span-2 mt-5">
                                Submit
                            </Button>
                        </form>
                    </TabsContent>
                </Tabs>
            )}

            {user && user.role == "student" && (
                <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                    <div className="space-y-4">
                        <div>
                            <Label htmlFor="name">Name</Label>
                            <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                        </div>
                        <div className="grid grid-cols-3 gap-3">
                            <div className="md:col-span-2">
                                <Label htmlFor="email">Email</Label>
                                <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="phone">Phone</Label>
                                <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="password">Password</Label>
                                <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="nim">NIM</Label>
                                <Input id="nim" name="nim" value={formData.nim} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="year">year</Label>
                                <Input id="year" name="year" value={formData.year} onChange={handleChange} required />
                            </div>
                        </div>
                    </div>

                    <Button type="submit" className="md:col-span-2 mt-5">
                        Submit
                    </Button>
                </form>
            )}

            {user && user.role == "lecturer" && (
                <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                    <div className="space-y-4">
                        <div>
                            <Label htmlFor="name">Name</Label>
                            <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                        </div>
                        <div className="grid grid-cols-3 gap-3">
                            <div className="md:col-span-2">
                                <Label htmlFor="email">Email</Label>
                                <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="phone">Phone</Label>
                                <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="password">Password</Label>
                                <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                            </div>
                            <div className="md:col-span-2">
                                <Label htmlFor="nip">NIP</Label>
                                <Input id="nip" name="nip" value={formData.nip} onChange={handleChange} required />
                            </div>
                        </div>
                    </div>

                    <Button type="submit" className="md:col-span-2 mt-5">
                        Submit
                    </Button>
                </form>
            )}

            {user && user.role == "admin" && (
                <form onSubmit={handleSubmit} className="grid grid-cols-1 p-2">
                    <div className="space-y-4">
                        <div>
                            <Label htmlFor="name">Name</Label>
                            <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
                        </div>
                        <div className="grid grid-cols-3 gap-3">
                            <div className="md:col-span-2">
                                <Label htmlFor="email">Email</Label>
                                <Input id="email" name="email" value={formData.email} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="phone">Phone</Label>
                                <Input id="phone" name="phone" value={formData.phone} onChange={handleChange} required />
                            </div>
                            <div>
                                <Label htmlFor="password">Password</Label>
                                <Input id="password" name="password" value={formData.password} onChange={handleChange} required />
                            </div>
                        </div>
                    </div>

                    <Button type="submit" className="md:col-span-2 mt-5">
                        Submit
                    </Button>
                </form>
            )}
        </>
    );
}